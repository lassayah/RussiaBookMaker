<?php
include('database_access.php');
$jsonresponse = array();

$users = $bdd->prepare('SELECT russia_user.username, points FROM russia_user WHERE russia_user.isadmin = 0 ORDER BY points DESC, username ASC');
$users->execute(array());
$i = 1;
$j = 1;
$prev_points = -1;
while ($userdata = $users->fetch())
{
	//if ($userdata['points'] == $prev_points)
	if ($i == 1)
	{
		$rank = $i . "er";
		$prev_points = $userdata['points'];
	}
	else
	{		
		if ($prev_points == $userdata['points'])
		{
			$i--;
		}
		else
		{
			$i = $j;
			$prev_points = $userdata['points'];
		}
			if ($i == 1)
				$rank = $i . "er";
			else
				$rank = utf8_encode ( $i . "ème");
	}

	$user = array(	'user' => $userdata['username'],
					'points' => $userdata['points'],
					'rank' => $rank);
	array_push($jsonresponse, $user);
		$i++;
		$j++;
}

echo json_encode($jsonresponse);

