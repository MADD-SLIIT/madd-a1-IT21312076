package com.example.myagri

data class TaskModel(
    var id: Int = 0,
    var title: String = "",
    var documentId: String? = null // Add this field for Firestore
)
