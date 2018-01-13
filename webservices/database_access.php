<?php

$user = 'root';

$password = 'root';

try

{

	$pdo_options[PDO::ATTR_ERRMODE] = PDO::ERRMODE_EXCEPTION;

	$pdo_options[PDO::MYSQL_ATTR_INIT_COMMAND] =  "SET NAMES utf8";

	//$bdd = new PDO('mysql:host=mysql51-60.perso;dbname=assayahjoel', $user, $password, $pdo_options);
	$bdd = new PDO('mysql:host=localhost;dbname=russiabookmaker', $user, $password, $pdo_options);
	

}

catch (Exception $e)

{

        die('Error : ' . $e->getMessage());

		echo $e->getMessage();

}
		
		?>