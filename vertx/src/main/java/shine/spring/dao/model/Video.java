package shine.spring.dao.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.Date;

public class Video implements Serializable{

    private static final long serialVersionUID = 3413847585043866059L;

    @QuerySqlField(index = true)
    private Integer vid;
    @QuerySqlField
    private String name;
    @QuerySqlField
    private String videoUrl;
    @QuerySqlField
    private String photoUrl;
    @QuerySqlField
    private Integer deleteFlag;
    @QuerySqlField
    private Date createdAt;
    @QuerySqlField
    private String creator;
    @QuerySqlField
    private String remark;

    public Video() {

    }

    public Video(Integer vid, String name, String videoUrl, String photoUrl, Integer deleteFlag, Date createdAt, String creator, String remark) {
        this.vid = vid;
        this.name = name;
        this.videoUrl = videoUrl;
        this.photoUrl = photoUrl;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.creator = creator;
        this.remark = remark;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "{" +
                "vid=" + vid +
                ", name='" + name + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", createdAt=" + createdAt +
                ", creator='" + creator + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}