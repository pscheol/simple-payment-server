package com.devpaik.payment.domain.user;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.user.field.CreateDtm;
import com.devpaik.payment.domain.user.field.Name;
import com.devpaik.payment.domain.user.field.UserId;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User implements Serializable {

    @Getter
    private final UserId userId;

    @Getter
    private final Name name;

    @Getter
    private final CreateDtm createDtm;

    @Getter
    private final Map<CurrencyCode, Wallet> wallets;

    private User(UserId userId, Name name, CreateDtm createDtm) {
        this.userId = userId;
        this.name = name;
        this.createDtm = createDtm;
        this.wallets = new HashMap<>();
    }

    public static User createUser(String userId, String name, LocalDateTime createDtm) {
        return new User(
               new UserId(userId),
               new Name(name),
               new CreateDtm(createDtm)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(name, user.name) &&
                Objects.equals(createDtm, user.createDtm) && Objects.equals(wallets, user.wallets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, createDtm, wallets);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name=" + name +
                ", createDtm=" + createDtm +
                ", wallets=" + wallets +
                '}';
    }
}
