package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postNumber;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Member member;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date postCreateDate;

    private String postTitle;

    private String postContents;

    private String postCategory;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = true)
    @CreationTimestamp
    private Date postAmendDate;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private int postReportCount;

    @Builder
    public Post(Member member, String postTitle, String postCategory, String postContents) {
        this.member = member;
        this.postCategory = postCategory;
        this.postTitle = postTitle;
        this.postContents = postContents;
        this.postStatus = PostStatus.Y;
        this.postReportCount = 0;
    }


    public void updateTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void updateContents(String postContents){
        this.postContents = postContents;
    }

    public void updateCategory(String postCategory){
        this.postCategory = postCategory;
    }

    public void addReportCount(){
        this.postReportCount = postReportCount+1;
    }

    public void toggleStatusN() {
        this.postStatus = PostStatus.N;
    }

    public void toggleStatusY() {
        this.postStatus = PostStatus.Y;
    }

}