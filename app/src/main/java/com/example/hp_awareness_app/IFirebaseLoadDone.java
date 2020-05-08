package com.example.hp_awareness_app;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<Article> articleList);
    void onFirebaseLoadFailed(String message);
}
