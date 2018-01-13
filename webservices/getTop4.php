<?php

include('../PHP/database_access.php');

  $top = $bdd->prepare('SELECT * FROM russia_top WHERE username = ?');

  $top->execute(array($_POST['username']));

  // Checking if there is a user with the same information username and password
  if ($top->rowCount() != 0) {
    $result = $top->fetch();

    $response = array(
      "team1" => $result[team1],
      "team2" => $result[team2],
      "team3" => $result[team3],
      "team4" => $result[team4]
    );

    print json_encode($response);
  } else {
    $response = array(
      "team1" => "",
      "team2" => "",
      "team3" => "",
      "team4" => ""
    );

    echo json_encode($response);
  }
  $top->closeCursor();

?>
