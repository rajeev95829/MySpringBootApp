<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Notification</title>
 <style> 
      
        table,  td { 
            border: 1px solid black; 
            border-collapse: collapse; 
            padding: 6px; 
            text-align:center; 
        } 
  
    </style>
</head>

<body>
    <p >
      Hello,
    </p>
    
    <p>
      Dear <b>${username}</b>, <br/><br/>There was request to reset the password for your account on the MyLoginApp Application. Please use the following details to login:<br/>
       Username: <b>${username}</b><br/>
       OTP: <b>${otp}</b><br/>
       Please validate it and reset your password. 
    </p>
   
      Regards, <br />
      <em>MyLoginApp TEAM</em>
    </p>
  </body>
</html>