Train => Station : loadPassengers(maxPassengers);
Station => util.Random : "min(maxPassengers,passengersWaiting)";
util.Random => Station : "numLoaded";
Station => Station : removeWaiting(numLoaded);
Station => Train : "numLoaded";