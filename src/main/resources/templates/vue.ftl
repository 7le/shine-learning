<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vue-Learning</title>
</head>
    <script src="/static/backend/js/vue.js"></script>
<body>
    <div id="app">
        {{ message }}
    </div>
    <div id="app-2">
        <span v-bind:title="message">
        鼠标悬停几秒钟查看此处动态绑定的提示信息！
        </span>
    </div>

    <script>
        var app = new Vue({
            el: '#app',
            data: {
                message: 'Hello Vue!'
            }
        })

        var app2 = new Vue({
            el: '#app-2',
            data: {
                message: '页面加载于 ' + new Date()
            }
        })
    </script>
</body>
</html>