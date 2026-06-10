twd97_to_lat(E,N){
	(phi1 - (nu * tan(phi1) / rho) * (D^2 / 2 - (5 + 3*T1 + 10*C1 - 4*C1^2 - 9*e2_prime) * D^4 / 24 + (61
	      + 90*T1 + 298*C1 + 45*T1^2 - 252*e2_prime - 3*C1^2) * D^6 / 720)) * (180 / pi)
	where D = x / (nu * k0)
		and T1 = tan(phi1)^2
		and C1 = e2_prime * cos(phi1)^2
		and nu = a / sqrt(1 - e2 * sin(phi1)^2)
		and rho = a * (1 - e2) / (1 - e2 * sin(phi1)^2)^1.5
	where phi1 = mu + (3*e1/2 - 27*e1^3/32)*sin(2*mu) + (21*e1^2/16 - 55*e1^4/32)*sin(4*mu) +
		(151*e1^3/96)*sin(6*mu) + (1097*e1^4/512)*sin(8*mu)
	where mu = M / (a * (1 - e2/4 - 3*e4/64 - 5*e6/256))
	where M = N / k0
		and x = E - 250000
	where e1 = (1 - sqrt(1 - e2)) / (1 + sqrt(1 - e2))
		and e2_prime = e2 / (1 - e2)
		and e6 = e2^3
		and e4 = e2^2
		and e2 = 0.0066943800229
		and f = 1 / 298.257222101
		and a = 6378137
		and k0 = 0.9999
}
twd97_to_lon(E,N) {
    (lambda0 + (D - (1 + 2*T1 + C1) * D^3 / 6 + (5 - 2*C1 + 28*T1 - 3*C1^2 + 8*e2_prime + 24*T1^2) * D^5
      / 120) / cos(phi1)) * (180 / pi)
    where D = x / (nu * k0)
		and T1 = tan(phi1)^2
		and C1 = e2_prime * cos(phi1)^2
		and nu = a / sqrt(1 - e2 * sin(phi1)^2)
    where phi1 = mu + (3*e1/2 - 27*e1^3/32)*sin(2*mu) + (21*e1^2/16 - 55*e1^4/32)*sin(4*mu) +
		(151*e1^3/96)*sin(6*mu) + (1097*e1^4/512)*sin(8*mu)
    where mu = M / (a * (1 - e2/4 - 3*e4/64 - 5*e6/256))
    where M = N / k0
		and x = E - 250000
    where lambda0 = 121 * (pi / 180)
		and e1 = (1 - sqrt(1 - e2)) / (1 + sqrt(1 - e2))
		and e2_prime = e2 / (1 - e2)
	    and e6 = e2^3
	    and e4 = e2^2
		and e2 = 0.0066943800229
		and a = 6378137
		and k0 = 0.9999
}

