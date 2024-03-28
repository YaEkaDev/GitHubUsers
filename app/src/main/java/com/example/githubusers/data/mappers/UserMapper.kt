package com.example.githubusers.data.mappers

import com.example.githubusers.data.database.UserInfoDbModel
import com.example.githubusers.data.network.UserInfoDto
import com.example.githubusers.data.network.UserShortInfoDto
import com.example.githubusers.data.models.UserInfo
import com.example.githubusers.data.models.UserShortInfo
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class UserMapper {
    fun mapDtoToEntity(userDto: UserInfoDto) = UserInfo(
        id = userDto.id,
        login = userDto.login,
        name = userDto.name,
        email = userDto.email,
        company = userDto.company,
        avatarUrl = userDto.avatarUrl,
        followers_count = userDto.followers_count,
        following_count = userDto.following_count,
        dateCreating = formatDate(userDto.dateCreating)
    )

    fun mapDbModelToEntity(userDbModel: UserInfoDbModel) = UserInfo(
        id = userDbModel.id,
        login = userDbModel.login,
        name = userDbModel.name,
        email = userDbModel.email,
        company = userDbModel.company,
        avatarUrl = userDbModel.avatarUrl,
        followers_count = userDbModel.followers_count,
        following_count = userDbModel.following_count,
        dateCreating = userDbModel.dateCreating
    )

    fun mapEntityToDbModel(user: UserInfo) = UserInfoDbModel(
        id = user.id,
        login = user.login,
        name = user.name,
        email = user.email,
        company = user.company,
        avatarUrl = user.avatarUrl,
        followers_count = user.followers_count,
        following_count = user.following_count,
        dateCreating = user.dateCreating
    )

    fun mapListDbModelToListEntity(list: List<UserInfoDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

    fun mapShortInfoDtoToEntity(userShortInfoDto: UserShortInfoDto) = UserShortInfo(
        id = userShortInfoDto.id,
        login = userShortInfoDto.login,
        avatarUrl = userShortInfoDto.avatarUrl,
        url = userShortInfoDto.url
    )


    fun mapListShortInfoDtoToListEntity(list: List<UserShortInfoDto>) = list.map {
        mapShortInfoDtoToEntity(it)
    }

    private fun formatDate(date: String?): String {

        val actual = OffsetDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val formatDateTime = actual.format(formatter)
        return formatDateTime

    }

}