// $ANTLR 3.1.2 Rule.g 2009-04-22 14:16:33

// The variable 'variable' is assigned but its value is never used.
#pragma warning disable 168, 219
// Unreachable code detected.
#pragma warning disable 162
namespace  TradingCommon.Traders.RuleParsing 
{

using System;
using Antlr.Runtime;
using IList 		= System.Collections.IList;
using ArrayList 	= System.Collections.ArrayList;
using Stack 		= Antlr.Runtime.Collections.StackList;


public partial class RuleLexer : Lexer {
    public const int RPAREN = 5;
    public const int SYMBOL_START = 10;
    public const int SYMBOL = 6;
    public const int NUMBER = 7;
    public const int WHITESPACE = 8;
    public const int DIGIT = 9;
    public const int EOF = -1;
    public const int LPAREN = 4;

    // delegates
    // delegators

    public RuleLexer() 
    {
		InitializeCyclicDFAs();
    }
    public RuleLexer(ICharStream input)
		: this(input, null) {
    }
    public RuleLexer(ICharStream input, RecognizerSharedState state)
		: base(input, state) {
		InitializeCyclicDFAs(); 

    }
    
    override public string GrammarFileName
    {
    	get { return "Rule.g";} 
    }

    // $ANTLR start "LPAREN"
    public void mLPAREN() // throws RecognitionException [2]
    {
    		try
    		{
            int _type = LPAREN;
    	int _channel = DEFAULT_TOKEN_CHANNEL;
            // Rule.g:9:8: ( '(' )
            // Rule.g:9:10: '('
            {
            	Match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally 
    	{
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public void mRPAREN() // throws RecognitionException [2]
    {
    		try
    		{
            int _type = RPAREN;
    	int _channel = DEFAULT_TOKEN_CHANNEL;
            // Rule.g:10:8: ( ')' )
            // Rule.g:10:10: ')'
            {
            	Match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally 
    	{
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "WHITESPACE"
    public void mWHITESPACE() // throws RecognitionException [2]
    {
    		try
    		{
            int _type = WHITESPACE;
    	int _channel = DEFAULT_TOKEN_CHANNEL;
            // Rule.g:37:2: ( ( ' ' | '\\n' | '\\t' | '\\r' | '\\u000C' )+ )
            // Rule.g:37:4: ( ' ' | '\\n' | '\\t' | '\\r' | '\\u000C' )+
            {
            	// Rule.g:37:4: ( ' ' | '\\n' | '\\t' | '\\r' | '\\u000C' )+
            	int cnt1 = 0;
            	do 
            	{
            	    int alt1 = 2;
            	    int LA1_0 = input.LA(1);

            	    if ( ((LA1_0 >= '\t' && LA1_0 <= '\n') || (LA1_0 >= '\f' && LA1_0 <= '\r') || LA1_0 == ' ') )
            	    {
            	        alt1 = 1;
            	    }


            	    switch (alt1) 
            		{
            			case 1 :
            			    // Rule.g:
            			    {
            			    	if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ' ) 
            			    	{
            			    	    input.Consume();

            			    	}
            			    	else 
            			    	{
            			    	    MismatchedSetException mse = new MismatchedSetException(null,input);
            			    	    Recover(mse);
            			    	    throw mse;}


            			    }
            			    break;

            			default:
            			    if ( cnt1 >= 1 ) goto loop1;
            		            EarlyExitException eee1 =
            		                new EarlyExitException(1, input);
            		            throw eee1;
            	    }
            	    cnt1++;
            	} while (true);

            	loop1:
            		;	// Stops C# compiler whinging that label 'loop1' has no statements

            	 _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally 
    	{
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "NUMBER"
    public void mNUMBER() // throws RecognitionException [2]
    {
    		try
    		{
            int _type = NUMBER;
    	int _channel = DEFAULT_TOKEN_CHANNEL;
            // Rule.g:42:2: ( ( '+' | '-' )? ( DIGIT )+ ( '.' ( DIGIT )+ )? )
            // Rule.g:42:4: ( '+' | '-' )? ( DIGIT )+ ( '.' ( DIGIT )+ )?
            {
            	// Rule.g:42:4: ( '+' | '-' )?
            	int alt2 = 2;
            	int LA2_0 = input.LA(1);

            	if ( (LA2_0 == '+' || LA2_0 == '-') )
            	{
            	    alt2 = 1;
            	}
            	switch (alt2) 
            	{
            	    case 1 :
            	        // Rule.g:
            	        {
            	        	if ( input.LA(1) == '+' || input.LA(1) == '-' ) 
            	        	{
            	        	    input.Consume();

            	        	}
            	        	else 
            	        	{
            	        	    MismatchedSetException mse = new MismatchedSetException(null,input);
            	        	    Recover(mse);
            	        	    throw mse;}


            	        }
            	        break;

            	}

            	// Rule.g:42:17: ( DIGIT )+
            	int cnt3 = 0;
            	do 
            	{
            	    int alt3 = 2;
            	    int LA3_0 = input.LA(1);

            	    if ( ((LA3_0 >= '0' && LA3_0 <= '9')) )
            	    {
            	        alt3 = 1;
            	    }


            	    switch (alt3) 
            		{
            			case 1 :
            			    // Rule.g:42:18: DIGIT
            			    {
            			    	mDIGIT(); 

            			    }
            			    break;

            			default:
            			    if ( cnt3 >= 1 ) goto loop3;
            		            EarlyExitException eee3 =
            		                new EarlyExitException(3, input);
            		            throw eee3;
            	    }
            	    cnt3++;
            	} while (true);

            	loop3:
            		;	// Stops C# compiler whinging that label 'loop3' has no statements

            	// Rule.g:42:26: ( '.' ( DIGIT )+ )?
            	int alt5 = 2;
            	int LA5_0 = input.LA(1);

            	if ( (LA5_0 == '.') )
            	{
            	    alt5 = 1;
            	}
            	switch (alt5) 
            	{
            	    case 1 :
            	        // Rule.g:42:27: '.' ( DIGIT )+
            	        {
            	        	Match('.'); 
            	        	// Rule.g:42:31: ( DIGIT )+
            	        	int cnt4 = 0;
            	        	do 
            	        	{
            	        	    int alt4 = 2;
            	        	    int LA4_0 = input.LA(1);

            	        	    if ( ((LA4_0 >= '0' && LA4_0 <= '9')) )
            	        	    {
            	        	        alt4 = 1;
            	        	    }


            	        	    switch (alt4) 
            	        		{
            	        			case 1 :
            	        			    // Rule.g:42:32: DIGIT
            	        			    {
            	        			    	mDIGIT(); 

            	        			    }
            	        			    break;

            	        			default:
            	        			    if ( cnt4 >= 1 ) goto loop4;
            	        		            EarlyExitException eee4 =
            	        		                new EarlyExitException(4, input);
            	        		            throw eee4;
            	        	    }
            	        	    cnt4++;
            	        	} while (true);

            	        	loop4:
            	        		;	// Stops C# compiler whinging that label 'loop4' has no statements


            	        }
            	        break;

            	}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally 
    	{
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "SYMBOL"
    public void mSYMBOL() // throws RecognitionException [2]
    {
    		try
    		{
            int _type = SYMBOL;
    	int _channel = DEFAULT_TOKEN_CHANNEL;
            // Rule.g:46:2: ( SYMBOL_START ( SYMBOL_START | DIGIT )* )
            // Rule.g:46:4: SYMBOL_START ( SYMBOL_START | DIGIT )*
            {
            	mSYMBOL_START(); 
            	// Rule.g:46:17: ( SYMBOL_START | DIGIT )*
            	do 
            	{
            	    int alt6 = 3;
            	    int LA6_0 = input.LA(1);

            	    if ( ((LA6_0 >= 'A' && LA6_0 <= 'Z') || LA6_0 == '_' || (LA6_0 >= 'a' && LA6_0 <= 'z')) )
            	    {
            	        alt6 = 1;
            	    }
            	    else if ( ((LA6_0 >= '0' && LA6_0 <= '9')) )
            	    {
            	        alt6 = 2;
            	    }


            	    switch (alt6) 
            		{
            			case 1 :
            			    // Rule.g:46:18: SYMBOL_START
            			    {
            			    	mSYMBOL_START(); 

            			    }
            			    break;
            			case 2 :
            			    // Rule.g:46:33: DIGIT
            			    {
            			    	mDIGIT(); 

            			    }
            			    break;

            			default:
            			    goto loop6;
            	    }
            	} while (true);

            	loop6:
            		;	// Stops C# compiler whining that label 'loop6' has no statements


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally 
    	{
        }
    }
    // $ANTLR end "SYMBOL"

    // $ANTLR start "SYMBOL_START"
    public void mSYMBOL_START() // throws RecognitionException [2]
    {
    		try
    		{
            // Rule.g:51:2: ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '_' ) )
            int alt7 = 3;
            switch ( input.LA(1) ) 
            {
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            	{
                alt7 = 1;
                }
                break;
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            	{
                alt7 = 2;
                }
                break;
            case '_':
            	{
                alt7 = 3;
                }
                break;
            	default:
            	    NoViableAltException nvae_d7s0 =
            	        new NoViableAltException("", 7, 0, input);

            	    throw nvae_d7s0;
            }

            switch (alt7) 
            {
                case 1 :
                    // Rule.g:51:4: ( 'a' .. 'z' )
                    {
                    	// Rule.g:51:4: ( 'a' .. 'z' )
                    	// Rule.g:51:5: 'a' .. 'z'
                    	{
                    		MatchRange('a','z'); 

                    	}


                    }
                    break;
                case 2 :
                    // Rule.g:51:17: ( 'A' .. 'Z' )
                    {
                    	// Rule.g:51:17: ( 'A' .. 'Z' )
                    	// Rule.g:51:18: 'A' .. 'Z'
                    	{
                    		MatchRange('A','Z'); 

                    	}


                    }
                    break;
                case 3 :
                    // Rule.g:51:30: ( '_' )
                    {
                    	// Rule.g:51:30: ( '_' )
                    	// Rule.g:51:31: '_'
                    	{
                    		Match('_'); 

                    	}


                    }
                    break;

            }
        }
        finally 
    	{
        }
    }
    // $ANTLR end "SYMBOL_START"

    // $ANTLR start "DIGIT"
    public void mDIGIT() // throws RecognitionException [2]
    {
    		try
    		{
            // Rule.g:56:2: ( ( '0' .. '9' ) )
            // Rule.g:56:4: ( '0' .. '9' )
            {
            	// Rule.g:56:4: ( '0' .. '9' )
            	// Rule.g:56:5: '0' .. '9'
            	{
            		MatchRange('0','9'); 

            	}


            }

        }
        finally 
    	{
        }
    }
    // $ANTLR end "DIGIT"

    override public void mTokens() // throws RecognitionException 
    {
        // Rule.g:1:8: ( LPAREN | RPAREN | WHITESPACE | NUMBER | SYMBOL )
        int alt8 = 5;
        switch ( input.LA(1) ) 
        {
        case '(':
        	{
            alt8 = 1;
            }
            break;
        case ')':
        	{
            alt8 = 2;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
        	{
            alt8 = 3;
            }
            break;
        case '+':
        case '-':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        	{
            alt8 = 4;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
        	{
            alt8 = 5;
            }
            break;
        	default:
        	    NoViableAltException nvae_d8s0 =
        	        new NoViableAltException("", 8, 0, input);

        	    throw nvae_d8s0;
        }

        switch (alt8) 
        {
            case 1 :
                // Rule.g:1:10: LPAREN
                {
                	mLPAREN(); 

                }
                break;
            case 2 :
                // Rule.g:1:17: RPAREN
                {
                	mRPAREN(); 

                }
                break;
            case 3 :
                // Rule.g:1:24: WHITESPACE
                {
                	mWHITESPACE(); 

                }
                break;
            case 4 :
                // Rule.g:1:35: NUMBER
                {
                	mNUMBER(); 

                }
                break;
            case 5 :
                // Rule.g:1:42: SYMBOL
                {
                	mSYMBOL(); 

                }
                break;

        }

    }


	private void InitializeCyclicDFAs()
	{
	}

 
    
}
}