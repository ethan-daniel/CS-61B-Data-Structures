import java.lang.Math;

public class Planet {
	static final double G = 6.67e-11;
    double xxPos, yyPos, xxVel, yyVel, mass;
    String imgFileName;

    public Planet(double xP, double yP, double xV,
                    double yV, double m, String img){
                        xxPos = xP;
                        yyPos = yP;
                        xxVel = xV;
                        yyVel = yV;
                        mass = m;
                        imgFileName = img;
                    }
    public Planet(Planet p){
                    xxPos = p.xxPos;
                    yyPos = p.yyPos;
                    xxVel = p.xxVel;
                    yyVel = p.yyVel;
                    mass = p.mass;
                    imgFileName = p.imgFileName;

    }

    public double calcDistance(Planet p){
        return (Math.sqrt((p.xxPos - xxPos) * (p.xxPos - xxPos) + (p.yyPos - yyPos) * (p.yyPos - yyPos)));
    }

	public double calcForceExertedBy(Planet p){
		return (G * mass * p.mass)/(calcDistance(p) * calcDistance(p));
	}
	
	public double calcForceExertedByX(Planet p){
		return calcForceExertedBy(p) * (p.xxPos - xxPos)/calcDistance(p);
	}
	
	public double calcForceExertedByY(Planet p){
		return calcForceExertedBy(p) * (p.yyPos - yyPos)/calcDistance(p);
	}
	
	public double calcNetForceExertedByX(Planet[] allPlanets){
		double net = 0;
		for (int i = 0; i != allPlanets.length; ++i){
			if (!allPlanets[i].equals(this)){
				net += calcForceExertedByX(allPlanets[i]);
			}
		}
		return net;
	}
	
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double net = 0;
		for (int i = 0; i != allPlanets.length; ++i){
			if (!allPlanets[i].equals(this)){
				net += calcForceExertedByY(allPlanets[i]);
			}
		}
		return net;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
