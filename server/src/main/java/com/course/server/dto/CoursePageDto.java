package com.course.server.dto;

public class CoursePageDto extends PageDto {

    private String status;

    private String categoryId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CoursePageDto{" +
                "status='" + status + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
