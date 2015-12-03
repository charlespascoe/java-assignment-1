using System;
using System.Collections.Generic;
using System.Linq;

public class Program {
    public static void Main(string[] args) {
        List<int> input = new List<int>(new int[] {1,2,4,5,7,8,9,11,12});

        Program.Imperative(input).ForEach(x => Console.Write(x + ","));
        Console.WriteLine();
        Program.Declarative(input).ForEach(x => Console.Write(x + ","));
        Console.WriteLine();
    }

    public static List<int> Imperative(List<int> input) {
        List<int> output = new List<int>();

        for (int i = 0; i < input.Count; i++) {
            int x = input[i];

            if (x % 2 == 0) {
                output.Add(x * x);
            }
        }

        return output;
    }

    public static List<int> Declarative(List<int> input) {
        return input.Where(x => x % 2 == 0).Select(x => x * x).ToList();
    }

}
