<?php

include('../PHP/database_access.php');

  $teams = $bdd->prepare('SELECT * FROM russia_team');
  $teams->execute(array());
$response = array();
while ($teamsdata = $teams->fetch())
{

    $team = array(
      "id" => $teamsdata['id'],
      "name" => $teamsdata['name'],
      "flag"=> $teamsdata['flag'],
      "group" => $teamsdata['group']
    );
    array_push($response, $team);
}
    echo json_encode($response);

?>
