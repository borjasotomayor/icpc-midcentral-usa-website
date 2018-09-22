/* ACM Mid-Central Regional Programming Contest */
/* Solution to Problem B, "Easier Done than Said?" */
/* by Dr. Eric Shade, Computer Science Dept. */
/* Southwest Missouri State University */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

FILE *in;
FILE *out;

char word[256];
int n;



int is_vowel(int ch)
{
    return ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u';
}



int contains_vowel(void)
{
    int i;
    
    for (i = 0; i < n; ++i) {
        if (is_vowel(word[i]))
            return 1;
    }
    
    return 0;
}
    
    

int three_consecutive_vowels(void) 
{
    int i;
    
    for (i = 0; i < (n - 2); ++i) {
        if (is_vowel(word[i]) && is_vowel(word[i+1]) && is_vowel(word[i+2]))
            return 1;
    }
    
    return 0;
}



int three_consecutive_consonants(void) 
{
    int i;
    
    for (i = 0; i < (n - 2); ++i) {
        if (!is_vowel(word[i]) && !is_vowel(word[i+1]) && !is_vowel(word[i+2]))
            return 1;
    }
    
    return 0;
}



int repeated_letter(void) 
{
    int i;
    
    for (i = 0; i < (n - 1); ++i) {
        if (word[i] == word[i+1] && word[i] != 'e' && word[i] != 'o')
            return 1;
    }
    
    return 0;
}




int can_say_it(void)
{
    return contains_vowel()
        && ! three_consecutive_vowels()
        && ! three_consecutive_consonants()
        && ! repeated_letter();
}



int main(void)
{
    in = fopen("say.in", "r");
    out = fopen("say.out", "w");
    
    while (strcmp(fgets(word, sizeof(word), in), "end\n") != 0) {
        n = strlen(word) - 1;
        word[n] = '\0';
                
        fprintf(out, "<%s> is ", word);

        if (! can_say_it()) fprintf(out, "not ");
    
        fprintf(out, "acceptable.\n");
    }
        
    fclose(in);
    fclose(out);
    
    return 0;
}
