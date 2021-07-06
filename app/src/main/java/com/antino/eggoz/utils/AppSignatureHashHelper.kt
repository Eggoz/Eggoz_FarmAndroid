package com.antino.eggoz.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.collections.ArrayList

class AppSignatureHashHelper( base: Context) : ContextWrapper(base) {
    companion object {
        val TAG = "AppSignatureHashHelper"

        val HASH_TYPE = "SHA-256";
        val NUM_HASHED_BYTES = 9;
        val NUM_BASE64_CHAR = 11;


        /**
         * Get all the app signatures for the current package
         *
         * @return
         */

        private fun hash(packageName:String, signature: String): String?
        {
            val appInfo = "$packageName $signature"
            try {
                var messageDigest = MessageDigest.getInstance(HASH_TYPE) as MessageDigest
                messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
                var hashSignature = messageDigest . digest ()

                // truncated into NUM_HASHED_BYTES
                hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES);
                // encode into Base64
                var base64Hash = Base64.encodeToString (hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
                base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR)

                return base64Hash;
            } catch ( e: NoSuchAlgorithmException) {
                Log.e(TAG, "No Such Algorithm Exception", e)
            }
            return null
        }
    }
    fun getAppSignatures() :ArrayList<String>{
        var appSignaturesHashs=ArrayList<String>()
        try {

            // Get all package details
            var packageName = getPackageName()
            var packageManager = getPackageManager()
            var signatures = packageManager.getPackageInfo(packageName,
                PackageManager.GET_SIGNATURES).signatures

            for (i in 0..signatures.size){
                val hash = hash(packageName, signatures[i].toCharsString());
                if (hash != null) {
                    appSignaturesHashs.add(String.format("%s", hash));
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "Package not found", e);
        }
        return appSignaturesHashs
    }
}