package com.andreyyurko.hhtinder.singleton

import com.andreyyurko.hhtinder.structures.Archive

class TransferSingleton {

    var archive: Archive? = null

    companion object {
        val instance = TransferSingleton()
    }

    fun setTransferArchive(archive: Archive?) {
        this.archive = archive
    }

    fun getTransferArchive(): Archive? {
        return archive
    }
}