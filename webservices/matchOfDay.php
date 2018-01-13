<?php
include('database_access.php');
$jsonresponse = array();
$type_match = $_POST['numberGroupMatch'];
$username = $_POST['username'];
$serverDate = date("Y-m-d H:i:s");
$teams = $bdd->prepare('SELECT team1, team2, rs.group, rs.id, rs.date, team1result, team2result, available, rt1.name as name1, rt2.name as name2,
rt1.flag as flag1, rt2.flag as flag2 FROM russia_schedule rs JOIN russia_team rt1 ON rs.team1 = rt1.id JOIN russia_team rt2 ON rs.team2 = rt2.id WHERE 
DATE(rs.date) = DATE(NOW())');
$teams->execute(array($begin, $end));

while ($teamsdata = $teams->fetch())
{
	$bet = $bdd->prepare('SELECT match_id, win FROM russia_bet WHERE username = ? AND match_id = ?');
	$bet->execute(array($username, $teamsdata['id']));
	if ($bet->rowCount() > 0)
	{
		$betdata = $bet->fetch();
		$response = array('team1' => $teamsdata['name1'],
					'team2' => $teamsdata['name2'],
					'group' => $teamsdata['group'] == NULL ? false : true,
					'bet' => $betdata['win'],
					'id' => $teamsdata['id'],
					'matchTime' => str_replace(" ", "T", $teamsdata['date'] . 'Z'),
					'dateServeur' => str_replace(" ", "T", date("Y-m-d H:i:s")) . 'Z',
					'resultTeam1' => $teamsdata['team1result'],
					'resultTeam2' => $teamsdata['team2result'],
					'flag1' => $teamsdata['flag1'],
					'flag2' => $teamsdata['flag2'],
					'prolongations' => $teamsdata['overtime']
		);
	}
	else
		$response = array('team1' => $teamsdata['name1'],
					'team2' => $teamsdata['name2'],
					'group' => $teamsdata['group'] == NULL ? false : true,
					'bet' => NULL,
					'id' => $teamsdata['id'],
					'matchTime' => str_replace(" ", "T", $teamsdata['date'] . 'Z'),
					'dateServeur' => str_replace(" ", "T", date("Y-m-d H:i:s")) . 'Z',
					'resultTeam1' => $teamsdata['team1result'],
					'resultTeam2' => $teamsdata['team2result'],
					'flag1' => $teamsdata['flag1'],
					'flag2' => $teamsdata['flag2'],
					'prolongations' => $teamsdata['overtime']
		);
	array_push($jsonresponse, $response);
}
//echo $username;
echo json_encode($jsonresponse);

