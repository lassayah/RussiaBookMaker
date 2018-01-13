<?php

include('database_access.php');

if ((isset($_POST['usernameConnect']) && !empty($_POST['usernameConnect'])) && (isset($_POST['passwordConnect']) && !empty($_POST['passwordConnect']))) { 
	  $hasaccount = $bdd->prepare('SELECT * FROM russia_user WHERE username = ? AND password = ?');
	  $hasaccount->execute(array($_POST['usernameConnect'], md5($_POST['passwordConnect'])));
	  // Checking if there is a user with the same information username and password
	  if ($hasaccount->rowCount() != 0)
	  {
		echo json_encode(array ('isLoggedIn' => 'true'));
	  }
	  else
	  {
		echo json_encode(array ('isLoggedIn' => 'false'));
	  }
	  $hasaccount->closeCursor();
   } 
   else { 
      echo json_encode(array ('isLoggedIn' => 'false'));
	  }  