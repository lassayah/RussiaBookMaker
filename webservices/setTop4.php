<?php

include('../PHP/database_access.php');
  $team1 = $_POST['team1'];
  $team2 = $_POST['team2'];
  $team3 = $_POST['team3'];
  $team4 = $_POST['team4'];
  $username = $_POST['username'];
  $top = $bdd->prepare('SELECT * FROM russia_top WHERE username = ?');

  $top->execute(array($username));

  if ($top->rowCount() != 0) {
    $settop = $bdd->prepare('UPDATE russia_top SET team1 = ?, team2 = ?, team3 = ?, team4 = ? WHERE username = ? ');
    $success = $settop->execute(array($team1, $team2, $team3, $team4, $username));
    print json_encode(array("success" => $success));
  } else {
    $settop = $bdd->prepare('INSERT INTO russia_top (username, team1, team2, team3, team4) VALUES (?, ?, ?, ?, ?)');
    $success = $settop->execute(array($username, $team1, $team2, $team3, $team4));
    print json_encode(array("success" => $success));
  }
  $settop->closeCursor();
  $top->closeCursor();
?>
