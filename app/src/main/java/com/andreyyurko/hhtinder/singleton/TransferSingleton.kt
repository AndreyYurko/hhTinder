package com.andreyyurko.hhtinder.singleton

import com.andreyyurko.hhtinder.structures.Card
import com.andreyyurko.hhtinder.structures.Profile

class TransferSingleton {

    var archiveCard: Card? = null
    var profile: Profile? = null

    companion object {
        val instance = TransferSingleton()
    }

    fun setTransferArchive(archiveCard: Card?) {
        this.archiveCard = archiveCard
    }

    fun getTransferArchive(): Card? {
        return archiveCard
    }

    fun setUserInfo(profile: Profile) {
        this.profile = profile
    }

    fun getUserInfo(): Profile? {
        return profile
    }
}