package metier;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Vector;



public class cd {
	public static String intToStatus[]={"Non  Vérifié", "Vérifié"};
	public static String intToMois[] = { "Janvier", "Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
	Hashtable projetParID;
	public cd() {
		// TODO Auto-generated constructor stub
	}
	public static Hashtable vectorToHastableByUnique(Vector vector, String parameter){
		Hashtable toReturn= new Hashtable();
		System.out.println("vectorToHastableByUnique():début");
		for (int i =0; i<vector.size();i++){
			Field f = null;
			try {
				f = vector.get(i).getClass().getDeclaredField(parameter);
				f.setAccessible(true);
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				try {
					//System.out.println(f.get(vector.get(i)));
					//System.out.println(((Projet) vector.get(i)).getLibelle());
					toReturn.put(f.get(vector.get(i)), vector.get(i));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return toReturn;
	}
	public static Hashtable vectorToHastableByParameter(Vector vector, String parameter){
		Hashtable toReturn= new Hashtable();
		Vector keys= new Vector();
		System.out.println("TrierVectorParValeur:début");
		
		for (int i =0; i<vector.size();i++){
			Field f = null;
			try {
				f = vector.get(i).getClass().getDeclaredField(parameter);
				f.setAccessible(true);
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				try {
					
						if (toReturn.containsKey(f.get(vector.get(i)))){
							
							Vector temp = (Vector) toReturn.get(f.get(vector.get(i)));
							temp.add(vector.get(i));
						}else{
							
							toReturn.put(f.get(vector.get(i)), new Vector());
							Vector temp = (Vector) toReturn.get(f.get(vector.get(i)));
							temp.add(vector.get(i));
						}
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return toReturn;
	}
	
	public static Vector getValFromVec(Vector vector, String parameter){
		Vector toReturn=new Vector();
		for (int i =0; i<vector.size();i++){
			try {
				Field f = vector.get(i).getClass().getDeclaredField(parameter);
				try {
					toReturn.add( f.get(vector.get(i)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		
		return toReturn;
		
	}

}
