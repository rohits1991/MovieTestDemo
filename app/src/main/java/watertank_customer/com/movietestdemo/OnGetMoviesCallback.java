package watertank_customer.com.movietestdemo;

import java.util.List;

import watertank_customer.com.movietestdemo.bean.Movie;

/**
 * Created by rohits on 4/5/19.
 */

public interface OnGetMoviesCallback {
    void onSuccess(int page, List<Movie> movies);

    void onError();
}
