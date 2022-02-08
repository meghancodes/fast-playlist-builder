package com.example.fastplaylistbuilder.results

sealed class AlertDialogServerAction {
    object NoResults : AlertDialogServerAction()
    object ServerFailure : AlertDialogServerAction()
    object DbAddFailure: AlertDialogServerAction()
}