package com.devpaik.user.secondary.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Serializable {

    @Id
    @Column(name = "user_id", length = 60, nullable = false)
    @Comment("사용자ID")
    @Getter
    private String userId;

    @Column(name = "name", length = 100)
    @Comment("이름")
    @Getter
    private String name;

    @CreatedDate
    @Column(name = "create_dtm", columnDefinition = "datetime default current_timestamp")
    @Comment("등록일")
    @Getter
    private LocalDateTime createDtm;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<WalletEntity> wallets;

    @Builder
    public UserEntity(String userId, String name, LocalDateTime createDtm, Set<WalletEntity> wallets) {
        this.userId = userId;
        this.name = name;
        this.createDtm = createDtm;
        this.wallets = wallets;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(name, that.name) &&
                Objects.equals(createDtm, that.createDtm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, createDtm);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", createDtm=" + createDtm +
                '}';
    }
}
