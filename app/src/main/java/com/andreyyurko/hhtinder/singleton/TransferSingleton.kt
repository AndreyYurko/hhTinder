package com.andreyyurko.hhtinder.singleton

import com.andreyyurko.hhtinder.structures.Card

class TransferSingleton {

    var archiveCard: Card? = null

    companion object {
        val instance = TransferSingleton()
    }

    fun setTransferArchive(archiveCard: Card?) {
        this.archiveCard = archiveCard
    }

    fun getTransferArchive(): Card? {
        return archiveCard
    }
}