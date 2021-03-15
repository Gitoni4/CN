package com.company;

import java.lang.Math;

import java.util.Arrays;
import java.util.Scanner;


public class Main
{
    double[][] A;
    int n;
    double[][] L;
    double[][] Lt;
    double[] d;

    void initMatrix()
    {
        Scanner scan = new Scanner(System.in);
        n = Integer.parseInt(scan.nextLine());

        A = new double[n][n];
        d = new double[n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                A[i][j] = Double.parseDouble(scan.nextLine());
            }

            d[i] = A[i][i];
        }

        scan.close();
    }

    void initL()
    {
        L = new double[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (j <= i)
                {
                    L[i][j] = A[i][j];
                }
                else
                {
                    L[i][j] = 0;
                }
            }
        }
    }

    boolean choleskiAlg()
    {
        double sum = 0;
        double mul = 0;

        for (int p = 0; p < n; p++)
        {
            sum = 0;
            for (int j = 0; j < p; j++)
            {
                sum += L[p][j] * L[p][j];
            }

            L[p][p] = Math.sqrt(d[p] - sum);

            if (L[p][p] == 0) return false;

            for (int i = p + 1; i < n; i++)
            {
                for (int j = 0; j < p; j++)
                {
                    mul += L[i][j] * L[p][j];
                }

                L[i][p] = (A[i][p] - mul) / L[p][p];
            }
        }
        return true;
    }

    void transpose()
    {
        Lt = new double[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                Lt[i][j] = L[j][i];
            }
        }
    }

    double det()
    {
        double res = 0;
        for (int i = 0; i < n; i++)
        {
            res = L[i][i] * Lt[i][i];
        }

        return res;
    }

    double[] subDirect(double[] b)
    {
        double[] x = new double[n];
        double sum;

        for (int i = 0; i < n; i++)
        {
            sum = 0;

            for (int j = 0; j < i; j++)
            {
                sum += L[i][j] * x[j];
            }

            x[i] = (b[i] - sum) / L[i][i];
        }

        return x;
    }

    double[] revDirect(double[] b)
    {
        double[] x = new double[n];
        double sum;

        for (int i = n - 1; i >= 0; i--)
        {
            sum = 0;

            for (int j = i + 1; j < n; j++)
            {
                sum += Lt[i][j] * x[j];
            }

            x[i] = (b[i] - sum) / Lt[i][i];
        }

        return x;
    }

    double[] mulMatrix(double[][] matrix, double[] x)
    {
        double[] y = new double[n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                y[i] += x[j] * A[i][j];
            }
        }

        return y;
    }

    double euclidNorm(double[] x)
    {
        double res = 0;

        for (int i = 0; i < n; i++)
        {
            res += x[i] * x[i];
        }

        return Math.sqrt(res);
    }

    void inverse()
    {
        double[] res = new double[n];
        double[][] Ainv = new double[n][n];

        for (int j = 0; j < n; j++)
        {
            double[] e = new double[n];
            e[j] = 1;

            res = revDirect(subDirect(e));

            for (int i = 0; i < n; i++)
            {
                Ainv[i][j] = res[i];
            }
        }

        printMatrix(Ainv);
    }

    void printMatrix(double[][] Matrix)
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                System.out.print(Matrix[i][j] + " ");
            }

            System.out.print("\n");
        }
    }

    public static void main(String[] args)
    {
        Main start = new Main();
        start.initMatrix();
        start.initL();

        if (!start.choleskiAlg())
        {
            System.out.println("The matrix is not positive definite");
            return;
        }

        start.printMatrix(start.L);
        System.out.println("///////////////");
        start.transpose();
        start.printMatrix(start.Lt);
        System.out.println("///////////////");

        System.out.println(start.det());
        System.out.println("///////////////");

        double[] b = new double[]{9, 35.0625, 61};
        double[] x;
        double[] mul;

        x = start.revDirect(start.subDirect(b));
        System.out.println(Arrays.toString(x));
        System.out.println("///////////////");
        mul = start.mulMatrix(start.A, x);
        System.out.println(Arrays.toString(mul));
        System.out.println("///////////////");

        double[] res = new double[start.n];

        for (int i = 0; i < start.n; i++)
        {
            res[i] = mul[i] - b[i];
        }

        System.out.println(start.euclidNorm(res));

        System.out.println("///////////////");
        start.inverse();
    }
}
