package watertank_customer.com.movietestdemo;

import java.util.List;

import watertank_customer.com.movietestdemo.bean.Genre;

/**
 * Created by rohits on 4/5/19.
 */

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}
