package com.yusril.storyapp.ui.createstory

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.R
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.domain.model.User
import com.yusril.storyapp.core.presentation.ViewModelFactory
import com.yusril.storyapp.core.vo.Status
import com.yusril.storyapp.databinding.ActivityCreateStoryBinding
import com.yusril.storyapp.ui.main.MainActivity
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class CreateStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateStoryBinding
    private lateinit var viewModel: UploadStoryViewModel
    private lateinit var user: User
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.create_story_title)
        }

        user = intent.getParcelableExtra<User>(USER) as User
        initViewModel()

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION,
                REQUEST_CODE_PERMISSION
            )
        }

        binding.apply {
            buttonCamera.setOnClickListener { startTakePhoto() }
            buttonGallery.setOnClickListener { startGallery() }
            buttonPost.setOnClickListener { uploadStory() }
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this, UserPreferences.getInstance(dataStore))
        viewModel = ViewModelProvider(this, factory)[UploadStoryViewModel::class.java]
    }

    private fun uploadStory() {
        val inputDesc = binding.inputDescription.text
        val isValidInput = inputDesc.toString().isNotEmpty() && inputDesc != null
        if (getFile != null && isValidInput && binding.inputDescription.error == null) {
            val file = getFile as File
            viewModel.uploadStory(user.token, file, inputDesc.toString()).observe(this){
                when(it.status) {
                    Status.LOADING -> showLoading(true)
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        showLoading(false)
                        MainActivity.start(this, user)
                    }
                }
            }

        } else {
            Toast.makeText(this, getString(R.string.upload_story_null), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.buttonPost.apply {
            visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            isEnabled = !isLoading
        }
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }


    private lateinit var currentPhotoPath: String
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                this,
                "com.yusril.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imagePreview.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            binding.imagePreview.setImageURI(selectedImg)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!allPermissionGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
        const val USER = "user"

        fun start(context: Activity, user: User) {
            val intent = Intent(context, CreateStoryActivity::class.java)
            intent.putExtra(USER, user)
            context.startActivity(intent)
        }
    }
}