<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${hostInfo.name}-${hostInfo.hostAddress}</title>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link rel="stylesheet" type="text/css" href="/xterm/xterm.css"/>
    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/xterm/addons/fit/fit.js?_=1"></script>
    <script type="text/javascript" src="/xterm/xterm.js"></script>
    <script type="text/javascript" src="/js/webssh.js?_=1"></script>
    <script>
        $(document).ready(function () {
            ShellClient.init('${accessToken}',${hostId});
        });
    </script>
</head>
<body style="padding: 0; margin: 0;">
<div id="terminal" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
</body>
</html>