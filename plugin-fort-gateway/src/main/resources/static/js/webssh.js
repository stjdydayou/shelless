Terminal.applyAddon(fit);
var ShellClient = {
    socket: null,
    accessToken: '',
    hostId: 0,
    terminal: new Terminal({
        rows: 20,
        cols: 140,
        screenKeys: false,
        useStyle: true,
        cursorBlink: true,
        convertEol: true
    }),
    clearTerminal: function () {
        this.terminal.write(String.fromCharCode(27, 91, 72, 27, 91, 50, 74, 27, 93, 48, 59, 114, 111, 111, 116, 64, 99, 105, 110, 101, 109, 97, 58, 126, 7));
    },
    connect: function () {
        var _this = this;
        _this.clearTerminal();

        var queryString = [];
        queryString.push(_this.accessToken);
        queryString.push(_this.hostId);
        queryString.push(this.terminal.cols);
        queryString.push(this.terminal.rows);

        var sockUrl = "ws://";

        if (window.location.protocol === "https:") {
            sockUrl = "wss://";
        }
        sockUrl += window.location.host + "/ws/terminal/" + queryString.join("/");


        _this.socket = new WebSocket(sockUrl);

        _this.socket.onerror = function (event) {
            _this.socket = null;
        };

        _this.socket.onopen = function (event) {
            _this.startHeartbeat();
        };

        _this.socket.onmessage = function (event) {
            var result = JSON.parse(event.data);

            if (result.code === 'xterm' || result.code === 'info') {
                _this.terminal.write(result.data);
            }
        };

        _this.socket.onclose = function (event) {
            _this.terminal.write("\n\n\u001b[31m您已经与服务器断开链接");
            _this.socket = null;
        };
    },
    startHeartbeat: function () {
        var _this = this;
        _this.heartbeatInterval = setInterval(function () {
            if (_this.socket != null) {
                _this.socket.send(JSON.stringify({'cmd': 'heartbeat', 'data': Math.random()}));
            } else {
                if (_this.heartbeatInterval) {
                    clearInterval(_this.heartbeatInterval);
                }
            }
        }, 3000);
    },
    resize: function () {
        this.terminal.fit();
    },
    init: function (accessToken, hostId) {
        var _this = this;
        _this.accessToken = accessToken;
        _this.hostId = hostId;

        _this.terminal.open(document.getElementById("terminal"), true);
        _this.terminal.focus();
        _this.terminal.fit();

        _this.connect();

        _this.terminal.on('data', function (data) {
            if (_this.socket == null) {
                _this.connect();
            } else {
                _this.socket.send(JSON.stringify({'cmd': 'sh', 'data': data}));
            }
        });

        $(window).resize(function () {
            _this.resize();
        });
    }
};