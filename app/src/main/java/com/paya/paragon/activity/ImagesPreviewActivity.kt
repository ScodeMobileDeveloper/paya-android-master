package com.paya.paragon.activity

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paya.paragon.R
import com.paya.paragon.api.postProperty.loadGalleryImage.SavedImageInfo
import com.paya.paragon.databinding.ActivityImagesPreviewBinding
import com.paya.paragon.utilities.AppConstant
import com.paya.paragon.utilities.Utils

class ImagesPreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagesPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_images_preview)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back_arrow_black)
        binding.toolbarTitle.text = " "
        binding.toolbarTitle.textSize = 16f

        val imagePath = intent?.extras?.getString(AppConstant.KEY_IMAGE_PATH)
        if (imagePath.isNullOrEmpty()) finish()
        (intent?.extras?.getSerializable(AppConstant.KEY_IMAGES) as? SavedImageInfo)?.let { data ->
            Utils.loadUrlImage(
                binding.image,
                Uri.parse(if (imagePath != null) imagePath + data.propertyImageName else data.propertyImageName)
                    .toString(),
                R.drawable.no_image_placeholder,
            )
        } ?: finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}