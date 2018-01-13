<?php

include('database_access.php');

if ((isset($_POST['username']) && !empty($_POST['username'])) && (isset($_POST['password']) && !empty($_POST['password']))) { 
	  $hasaccount = $bdd->prepare('SELECT * FROM russia_user WHERE username = ?');
	  $hasaccount->execute(array($_POST['username']));
	  // Checking if there is a user with the same information username and password
	  if ($hasaccount->rowCount() != 0)
	  {
		echo json_encode(array ('isLoggedIn' => 'false'));
	  }
	  else
	  {
	  	$createaccount = $bdd->prepare('INSERT INTO russia_user (username, password) VALUES (?, ?)');
	  	$createaccount->execute(array($_POST['username'], md5($_POST['password'])));
		echo json_encode(array ('isLoggedIn' => 'true'));
	  }
	  $hasaccount->closeCursor();
   } 
   else { 
      echo json_encode(array ('isLoggedIn' => 'false'));
	  }  