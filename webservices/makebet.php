<?php
include('database_access.php');
$jsonresponse = array();
$id_match = $_POST['id'];
$username = $_POST['username'];
$serverDate = date("Y-m-d H:i:s");
$rawDate = date("Y-m-d H:i:s", strtotime($_POST['matchTime']));
$bet = $_POST['resultBet'];
$raw = strtotime($rawDate);//DateTime::createFromFormat('yyyy-MM-dd hh:mm:ss', $rawDate);
$server = strtotime($serverDate);//DateTime::createFromFormat('Y-m-d h:i:s', $serverDate);
//$getdate = $bdd->prepare("SELECT rs.date as matchdate FROM russia_schedule rs WHERE id = ?");
//$getdate->execute(array($id_match));
//$getdatedata = $getdate->fetch();
//$rawDate = strtotime($getdatedata['matchdate']);
if ($server < $raw)
{
	$teambet = $bdd->prepare('SELECT name FROM russia_team WHERE id = ?');
	$teambet->execute(array($bet));
	$teambetdata = $teambet->fetch();
	$check = $bdd->prepare("SELECT id FROM russia_bet WHERE match_id = ? AND username = ?");
	$check->execute(array($id_match, $username));
	if ($check->rowCount() == 0)
	{
		$makebet = $bdd->prepare("INSERT INTO russia_bet (username, match_id, win) VALUES (?, ?, ?)");
		$success = $makebet->execute(array($username, $id_match, $bet));
		if ($bet == 33)
			$resultBet = "Nul";
		else
			$resultBet = $teambetdata['name'];
		$response = array('betOk' => $success, 'resultBet' => $resultBet);
	}
	else
	{
		$makebet = $bdd->prepare("UPDATE russia_bet SET win = ? WHERE match_id = ? AND username = ?");
		$success = $makebet->execute(array($bet, $id_match, $username));
		if ($bet == 33)
			$resultBet = "Nul";
		else
			$resultBet = $teambetdata['name'];
		$response = array('betOk' => $success, 'resultBet' => $resultBet);
	}
}
else
	$response = array('betOk' => "late");
echo json_encode($response);
