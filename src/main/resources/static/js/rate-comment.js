/**
 * Created by Samsung on 29.04.2017.
 */
var photos;
var cur;
var img_holder = document.getElementById("current_photo");
var comment_textfield = document.getElementById("comment_text");
var comment_holder = document.getElementById("comment_holder");

var get_photos = function () {
    $.ajax({
        type: 'GET',
        url: '/photo/get/photos/' + cur_id,
        success: function (response) {
            photos = response;
            cur = 0;
            img_holder.setAttribute('src', 'http://res.cloudinary.com/kondrat/image/upload/' + photos[cur].path);
            setInterval(function () {
                update_all();
            }, 30000);
            update_all();
        }
    });
}

var next_photo = function () {
    cur++;
    cur %= photos.length;
    img_holder.setAttribute('src', 'http://res.cloudinary.com/kondrat/image/upload/' + photos[cur].path);
    update_all();
}

var prev_photo = function () {
    cur += photos.length - 1;
    cur %= photos.length;
    img_holder.setAttribute('src', 'http://res.cloudinary.com/kondrat/image/upload/' + photos[cur].path);
    update_all();
}

var update_all = function () {
    $.ajax({
        type: 'GET',
        url: '/photo/rating/' + photos[cur].id,
        success: function (response) {
            console.log(response, 'rating');
            $('.value').html(response);
        }
    });
    $.ajax({
        type: 'GET',
        url: '/photo/rating/' + photos[cur].id + '/my',
        success: function (response) {
            console.log(response, 'rating my');
            if (response != 0) {
                $('#example-fontawesome-o').barrating('set', response);
                $('.stars-example-fontawesome-o .clear-rating')
                    .removeClass('hidden');
            } else {
                $('#example-fontawesome-o').barrating('set', '');
                $('.stars-example-fontawesome-o .clear-rating')
                    .addClass('hidden');
            }
        }
    });
    $.ajax({
        type: 'GET',
        url: '/photo/get/comment/' + photos[cur].id,
        success: function (response) {
            console.log(response, 'comment');
            comment_holder.innerHTML = '';
            response.forEach(function (item, i, response) {
                var li = document.createElement('li');
                li.className = 'clearfix';
                li.innerHTML = ''
                    + '<div class="chat-body clearfix">'
                    + '<div class="header">'
                    + '<strong class="primary-font">'
                    + '<a href="/profile/' + item.createdBy + '">'
                    + '<p>' + item.sender + '</p>'
                    + '</a>'
                    + '</strong>'
                    + '</div>'
                    + '<p>' + item.text + '</p>'
                    + '</div>';
                comment_holder.appendChild(li);
            })
        }
    });
}

var comment = function () {
    $.ajax({
        type: 'POST',
        url: '/photo/comment/' + photos[cur].id,
        data: ({
            "text": comment_textfield.value
        }),
        success: function () {
            comment_textfield.value = '';
            update_all();
        }
    });
}

var rate = function (value) {
    console.log('text', value)
    $.ajax({
        type: 'POST',
        url: '/photo/rate/' + photos[cur].id,
        data: ({
            "rate": value
        }),
        success: function () {
            update_all();
        }
    });
}

$(function () {
    $('.stars-example-fontawesome-o .clear-rating').on('click', function (event) {
        event.preventDefault();

        $('#example-fontawesome-o')
            .barrating('clear');
    });

    $('#example-fontawesome-o').barrating({
        theme: 'fontawesome-stars-o',
        showSelectedRating: false,
        initialRating: 0,
        silent: true,
        onSelect: function (value, text) {
            if (!value) {
                $('#example-fontawesome-o')
                    .barrating('clear');
                rate(-1);
            } else {
                $('.stars-example-fontawesome-o .clear-rating')
                    .removeClass('hidden');
                rate(value);
            }
        },
        onClear: function (value, text) {
            $('.stars-example-fontawesome-o .clear-rating')
                .addClass('hidden');
            rate(-1);
        }
    });

    get_photos();
});