var gulp = require('gulp');
var concat = require('gulp-concat');
var cleanCSS = require('gulp-clean-css');
var concatCss = require('gulp-concat-css');
var minify = require('gulp-minify');
var jshint = require('gulp-jshint');

gulp.task('lint', function () {
    gulp.src('js/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('minify-css', function () {
    return gulp.src('css/*.css')
        .pipe(concatCss("joshua.min.css"))
        .pipe(cleanCSS({debug: true}, function (details) {
            console.log(details.name + ': ' + details.stats.originalSize);
            console.log(details.name + ': ' + details.stats.minifiedSize);
        }))
        .pipe(gulp.dest('../public/assets/css/'));
});

gulp.task("minify-js", function () {
    return gulp.src("js/*.js")
        .pipe(concat("joshua.js"))
        .pipe(minify({
            ext: {
                min: ".min.js"
            },
            noSource: true
        }))
        .pipe(gulp.dest('../public/assets/js/'))
});

gulp.task("default", function () {
    gulp.run("minify-js", "minify-css");
});