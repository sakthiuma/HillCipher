/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hillcipher;
import java.util.*;
import java.math.*;

/**
 *
 * @author Sakthi
 */
public class HillCipher {

    /**
     * @param args the command line arguments
     */
   public static int matrix[][]=new int[3][3];
   public static int textMatrix[]=new int[3];
   public static int outputMatrix[]=new int[3];
   public static String text,key;
   public static StringBuilder temp=new StringBuilder();
   public static int determinant=0;
   public static int order;
   public static int inverse_matrix[][]=new int[3][3];
   
   private static void generateKeyMatrix() {
         //To change body of generated methods, choose Tools | Templates.
        int i,j,k=0,diff;
        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                matrix[i][j]=(key.charAt(k))%97;//generating the matrix;
                k++;
            }
        }       
        
    }
   
   private static void generateTextMatrix(StringBuilder temp) {
        int i;
        
        for(i=0;i<3;i++)
        {
            textMatrix[i]=(temp.charAt(i))%97;
        }
        /*for(i=0;i<3;i++)
            System.out.println(textMatrix[i]);*/
    }

    private static void HillCipherEncrypt() {
        int i,j,k;
        for(i=0;i<3;i++)
        {
            outputMatrix[i]=0;
            for(j=0;j<3;j++)
            {
                outputMatrix[i]+=textMatrix[j]*matrix[i][j];
                outputMatrix[i]=outputMatrix[i]%26;
            }
            System.out.print((char)(outputMatrix[i]+97));
        }
        
    }

    private static void calculateDeterminant() {
        int i,j;
        BigInteger deter,mod_val,invers_deter,mod_input,mod_output;
        
        for(i=0;i<3;i++)
            determinant = determinant + (matrix[0][i] * (matrix[1][(i+1)%3] * matrix[2][(i+2)%3] - matrix[1][(i+2)%3] * matrix[2][(i+1)%3]));
        determinant=determinant%26;
        deter=BigInteger.valueOf(determinant);
        mod_val=BigInteger.valueOf(26);
        invers_deter=deter.modInverse(mod_val);
        //determinant=invers_deter.intValue();
        //System.out.println("determinant"+determinant+"inverse_determinant"+invers_deter.intValue());
        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 3; ++j)
            {
		inverse_matrix[i][j]=(((matrix[(j+1)%3][(i+1)%3] * matrix[(j+2)%3][(i+2)%3]) - (matrix[(j+1)%3][(i+2)%3] * matrix[(j+2)%3][(i+1)%3])));
                mod_input=BigInteger.valueOf(inverse_matrix[i][j]);
                mod_output=mod_input.mod(mod_val);
                inverse_matrix[i][j]=(mod_output.intValue())*invers_deter.intValue();
                mod_input=BigInteger.valueOf(inverse_matrix[i][j]);
                mod_output=mod_input.mod(mod_val);
                inverse_matrix[i][j]=mod_output.intValue();
                //now find inverse_matrix mod 26. then find a method to calculate the inverse of the determinant and multiply 
                //inverse_matrix with it and again take mod 26 which gives the necessary inverse_matrix.
            }
            }
        /*for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
                System.out.print(inverse_matrix[i][j]+" ");
            System.out.println();
        }*/
      
    }
    
   static void HillCipherDecrypt()
   {
       int i,j,k;
       //System.out.println("in decryption");
        for(i=0;i<3;i++)
        {
            outputMatrix[i]=0;
            for(j=0;j<3;j++)
            {
                outputMatrix[i]+=textMatrix[j]*inverse_matrix[i][j];
                outputMatrix[i]=outputMatrix[i]%26;
            }
            System.out.print((char)(outputMatrix[i]+97));
        }
   }
   public static void main(String[] args) {
        // TODO code application logic here
       
       
       int choice;
       Scanner s=new Scanner(System.in);
       System.out.println("Enter 1 for Encryption \n 2 for decryption \n");
       choice=s.nextInt();
       System.out.println("Enter the string for applying hill cipher");
        
       text=s.next();
       
       //System.out.println("Enter the order of the matrix");
       //order=s.nextInt();
       System.out.println("Enter the key string");
       key=s.next();
       key=key.toLowerCase();
       generateKeyMatrix();
       int temp_order=0;
       int i=0;
       switch(choice)
       {
           case 1:
               while(i<text.length())
               {
                   while(temp_order<3)
                   {
               
                       temp.append(text.charAt(temp_order));
                       //System.out.println(temp.charAt(temp_order));
                       temp_order++;
                       i++;
                   }
                   //System.out.print(temp);
                   generateTextMatrix(temp);
                   HillCipherEncrypt();
                   temp_order=0;
                   temp.delete(0,2);
               }
               break;
           case 2:
               while(i<text.length())
               {
                   while(temp_order<3)
                   {
               
                       temp.append(text.charAt(temp_order));
                       //System.out.println(temp.charAt(temp_order));
                       temp_order++;
                       i++;
                   }
                   //System.out.print(temp);
                   calculateDeterminant();
                   generateTextMatrix(temp);
                   HillCipherDecrypt();
                   temp_order=0;
                   temp.delete(0,2);
               }
               //calculateDeterminant();
               
               //HillCipherDecrypt();
               break;
       }
    }
}
