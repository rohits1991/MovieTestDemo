package watertank_customer.com.movietestdemo;

import watertank_customer.com.movietestdemo.bean.Movie;

/**
 * Created by rohits on 4/5/19.
 */

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);

    void onError();
}
