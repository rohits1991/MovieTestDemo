package watertank_customer.com.movietestdemo.repo;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import watertank_customer.com.movietestdemo.OnGetGenresCallback;
import watertank_customer.com.movietestdemo.OnGetMovieCallback;
import watertank_customer.com.movietestdemo.OnGetMoviesCallback;
import watertank_customer.com.movietestdemo.TMDbApi;
import watertank_customer.com.movietestdemo.bean.GenresResponse;
import watertank_customer.com.movietestdemo.bean.Movie;
import watertank_customer.com.movietestdemo.bean.MoviesResponse;

/**
 * Created by rohits on 4/5/19.
 */

public class MoviesRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String UPCOMING = "upcoming";
    public static final String API = "f5de308ae44088eb35e08474258cefe2";
    private static MoviesRepository repository;
    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    public void getMovies(int page, String sortBy, final OnGetMoviesCallback callback) {
        Callback<MoviesResponse> call = new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse != null && moviesResponse.getMovies() != null) {
                        callback.onSuccess(moviesResponse.getPage(), moviesResponse.getMovies());
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                callback.onError();
            }
        };

        switch (sortBy) {
            case TOP_RATED:
                api.getTopRatedMovies(API, LANGUAGE, page)
                        .enqueue(call);
                break;
            case UPCOMING:
                api.getUpcomingMovies(API, LANGUAGE, page)
                        .enqueue(call);
                break;
            case POPULAR:
            default:
                api.getPopularMovies(API, LANGUAGE, page)
                        .enqueue(call);
                break;
        }
    }

    public void getGenres(final OnGetGenresCallback callback) {
        api.getGenres(API, LANGUAGE)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                        if (response.isSuccessful()) {
                            GenresResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                callback.onSuccess(genresResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenresResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getMovie(int movieId, final OnGetMovieCallback callback) {
        api.getMovie(movieId, API, LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
