/* ACM '98 Mid-Central Regional Programming Contest
 * Problem 5: Do the Untwist
 * by Eric Shade, SMSU
 */

#include <stdio.h>
#include <string.h>

/* This program can encrypt text from untwist.txt and create an input
 * file untwist.in, or it can decrypt untwist.in and produce untwist.out
 * Set the following flag to 0 to encrypt or 1 to decrypt.
 */

#define DECRYPT_MODE 1

/* The description refers to four arrays, but here I just use two. */

unsigned char cipher[72];
unsigned char plain[72];
int key;
int n;


int char_to_code (unsigned char ch)
{
  if (ch == '_')
    return 0;
  else if (ch == '.')
    return 27;
  else
    return ch - 'a' + 1;
}


char code_to_char (int code)
{
  static unsigned char decode[] = "_abcdefghijklmnopqrstuvwxyz.";
  return decode[code];
}


void convert_to_chars (unsigned char s[])
{
  int i;
  for (i = 0; i < n; ++i)
    s[i] = code_to_char (s[i]);
  s[i] = '\0';
}


void convert_to_codes (unsigned char s[])
{
  int i;
  for (i = 0; i < n; ++i)
    s[i] = char_to_code (s[i]);
}


void encrypt_codes (void)
{
  int i;
  for (i = 0; i < n; ++i)
  {
    int code = plain[(key*i) % n] - (i % 28);
    if (code < 0) code += 28;
    cipher[i] = code;
  }
}


void decrypt_codes (void)
{
  int i;
  for (i = 0; i < n; ++i)
    plain[(key*i) % n] = (cipher[i] + i) % 28;
}


#if DECRYPT_MODE

void main ()
{
  FILE *in = fopen ("untwist.in", "r");
  FILE *out = fopen ("untwist.out", "w");

  fscanf (in, "%d ", &key);
  while (key != 0)
  {
    fscanf (in, "%s\n", cipher);
    n = strlen (cipher);
    convert_to_codes (cipher);
    decrypt_codes ();
    convert_to_chars (plain);
    fprintf (out, "%s\n", plain);
    fscanf (in, "%d ", &key);
  }

  fclose (in);
  fclose (out);
}

#else

void main ()
{
  FILE *in = fopen ("untwist.txt", "r");
  FILE *out = fopen ("untwist.in", "w");

  fscanf (in, "%d ", &key);
  while (key != 0)
  {
    fscanf (in, "%s\n", plain);
    n = strlen (plain);
    convert_to_codes (plain);
    encrypt_codes ();
    convert_to_chars (cipher);
    fprintf (out, "%d %s\n", key, cipher);
    fscanf (in, "%d ", &key);
  }

  fprintf (out, "0\n");
  
  fclose (in);
  fclose (out);
}

#endif /* DECRYPT_MODE */
