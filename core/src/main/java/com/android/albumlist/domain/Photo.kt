package com.android.albumlist.domain

import java.io.Serializable


data class Photo(
    var id: Int=0,
    var albumId: Int=0,
    var title: String="",
    var url: String="",
    var thumbnail: String=""
): Serializable {
    companion object {
        val EMPTY = Photo(0, 0, "", "", "")
    }
}

/* model class that contains @Entity(tableName) and all columns*/
/*class Photo
{

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id = 0

    @ColumnInfo(name = "albumId")
    @SerializedName("albumId")
    @Expose
    var albumId = 0

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title = ""

    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    var url = ""

    @ColumnInfo(name = "thumbnailUrl")
    @SerializedName("thumbnailUrl")
    @Expose
    var thumbnailUrl = ""


    constructor()

    constructor(albumId: Int, id: Int, title: String, url: String, thumbnailUrl: String) {
        this.albumId = albumId
        this.id = id
        this.title = title
        this.url = url
        this.thumbnailUrl = thumbnailUrl
    }


    override fun toString(): String {
        return "Photo(albumId='$albumId', id='$id', title='$title', url='$url', thumbnailUrl='$thumbnailUrl')"
    }


}*/