<?php
include('database_access.php');
$jsonresponse = array();
//$type_match = $_POST['numberGroupMatch'];
$username = $_POST['username'];
$serverDate = date("Y-m-d H:i:s");
$filter = $_POST['filter'];
if ($filter == "global")
{
/*$begin = 1;
$end = 10;
if ($type_match == "0")
{
	$begin = 1;
	$end = 16;
}
else if ($type_match == "1")
{
	$begin = 17;
	$end = 32;
}
else if ($type_match == "2")
{
	$begin = 33;
	$end = 48;
}
else if ($type_match == "3")
{
	$begin = 49;
	$end = 56;
}
else if ($type_match == "4")
{
	$begin = 57;
	$end = 60;
}
else if ($type_match == "5")
{
	$begin = 61;
	$end = 62;
}
else
{
	$begin = 63;
	$end = 64;
}

$teams = $bdd->prepare('SELECT team1, team2, rs.group as groupe, rs.id, rs.date, team1result, team2result, available, rt1.name as name1, rt2.name as name2,
rt1.flag as flag1, rt2.flag as flag2 FROM russia_schedule rs JOIN russia_team rt1 ON rs.team1 = rt1.id JOIN russia_team rt2 ON rs.team2 = rt2.id WHERE rs.id BETWEEN ? AND ?');*/
$teams = $bdd->prepare('SELECT team1, team2, rs.group as groupe, rs.id, rs.date, rs.overtime, team1result, team2result, available, rt1.name as name1, rt2.name as name2,
rt1.flag as flag1, rt2.flag as flag2, tempname1, tempname2, rt1.id as team1id, rt2.id as team2id FROM russia_schedule rs LEFT OUTER JOIN russia_team rt1 ON rs.team1 = rt1.id LEFT OUTER JOIN russia_team rt2 ON rs.team2 = rt2.id LEFT OUTER JOIN russia_namematch rn ON rn.id_match = rs.id ORDER BY rs.id');
}
else
{
	$teams = $bdd->prepare('SELECT team1, team2, rs.group as groupe, rs.id, rs.date, rs.overtime, team1result, team2result, available, rt1.name as name1, rt2.name as name2,
rt1.flag as flag1, rt2.flag as flag2, tempname1, tempname2, rt1.id as team1id, rt2.id as team2id FROM russia_schedule rs LEFT OUTER JOIN russia_team rt1 ON rs.team1 = rt1.id LEFT OUTER JOIN russia_team rt2 ON rs.team2 = rt2.id LEFT OUTER JOIN russia_namematch rn ON rn.id_match = rs.id WHERE DATE(rs.date) = DATE(NOW()) ORDER BY rs.id');
}
$teams->execute(array());

while ($teamsdata = $teams->fetch())
{
	$bet = $bdd->prepare('SELECT match_id, win FROM russia_bet rb WHERE username = ? AND match_id = ?');
	$bet->execute(array($username, $teamsdata['id']));
		$betdata = $bet->fetch();
		if ($bet->rowCount() > 0)
		{
			if ($betdata['win'] == 33)
				$win = "Nul";
			else
			{
				$bet2 = $bdd->prepare('SELECT name FROM russia_team WHERE id = ?');
				$bet2->execute(array($betdata['win']));
				$bet2data = $bet2->fetch();
				$win = $bet2data['name'];
			}
		}
		$response = array('team1' => $teamsdata['name1'] != NULL ? $teamsdata['name1'] : $teamsdata['tempname1'],
					'team2' => $teamsdata['name2'] != NULL ? $teamsdata['name2'] : $teamsdata['tempname2'],
					'group' => $teamsdata['groupe'],
					'team1Id' => $teamsdata['team1id'],
					'team2Id' => $teamsdata['team2id'],
					'resultBet' => $bet->rowCount() > 0 ? $win : NULL,
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

