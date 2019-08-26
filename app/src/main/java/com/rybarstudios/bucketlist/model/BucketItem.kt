package com.rybarstudios.bucketlist.model

import java.io.Serializable

class BucketItem (
    var name: String,
    var description: String,
    var completed: Boolean,
    var indexId: Int
) : Serializable