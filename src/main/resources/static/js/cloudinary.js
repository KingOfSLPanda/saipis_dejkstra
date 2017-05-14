/**
 * Created by Samsung on 26.04.2017.
 */
document.getElementById("upload_widget_opener").addEventListener("click", function () {
    cloudinary.openUploadWidget({
            cloud_name: 'kondrat', upload_preset: 'tduqgmt1',
            sources: ['local', 'url'], keep_widget_open: false,
            resource_type: 'image'
        },
        function (error, result) {
            if (result != undefined) {
                $.ajax({
                    url: "/loadPhoto",
                    data: JSON.stringify(result),
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json"
                });
            }
            console.log(error, result)
        }
    );
}, false);