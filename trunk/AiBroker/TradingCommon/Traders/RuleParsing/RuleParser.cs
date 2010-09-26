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



using Antlr.Runtime.Tree;

public partial class RuleParser : Parser
{
    public static readonly string[] tokenNames = new string[] 
	{
        "<invalid>", 
		"<EOR>", 
		"<DOWN>", 
		"<UP>", 
		"LPAREN", 
		"RPAREN", 
		"SYMBOL", 
		"NUMBER", 
		"WHITESPACE", 
		"DIGIT", 
		"SYMBOL_START"
    };

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



        public RuleParser(ITokenStream input)
    		: this(input, new RecognizerSharedState()) {
        }

        public RuleParser(ITokenStream input, RecognizerSharedState state)
    		: base(input, state) {
            InitializeCyclicDFAs();

             
        }
        
    protected ITreeAdaptor adaptor = new CommonTreeAdaptor();

    public ITreeAdaptor TreeAdaptor
    {
        get { return this.adaptor; }
        set {
    	this.adaptor = value;
    	}
    }

    override public string[] TokenNames {
		get { return RuleParser.tokenNames; }
    }

    override public string GrammarFileName {
		get { return "Rule.g"; }
    }


    public class sexpr_return : ParserRuleReturnScope
    {
        private CommonTree tree;
        override public object Tree
        {
        	get { return tree; }
        	set { tree = (CommonTree) value; }
        }
    };

    // $ANTLR start "sexpr"
    // Rule.g:18:1: sexpr : list EOF ;
    public RuleParser.sexpr_return sexpr() // throws RecognitionException [1]
    {   
        RuleParser.sexpr_return retval = new RuleParser.sexpr_return();
        retval.Start = input.LT(1);

        CommonTree root_0 = null;

        IToken EOF2 = null;
        RuleParser.list_return list1 = default(RuleParser.list_return);


        CommonTree EOF2_tree=null;

        try 
    	{
            // Rule.g:19:2: ( list EOF )
            // Rule.g:19:4: list EOF
            {
            	root_0 = (CommonTree)adaptor.GetNilNode();

            	PushFollow(FOLLOW_list_in_sexpr72);
            	list1 = list();
            	state.followingStackPointer--;

            	adaptor.AddChild(root_0, list1.Tree);
            	EOF2=(IToken)Match(input,EOF,FOLLOW_EOF_in_sexpr74); 
            		EOF2_tree = (CommonTree)adaptor.Create(EOF2);
            		adaptor.AddChild(root_0, EOF2_tree);


            }

            retval.Stop = input.LT(-1);

            	retval.Tree = (CommonTree)adaptor.RulePostProcessing(root_0);
            	adaptor.SetTokenBoundaries(retval.Tree, (IToken) retval.Start, (IToken) retval.Stop);
        }
        catch (RecognitionException re) 
    	{
            ReportError(re);
            Recover(input,re);
    	// Conversion of the second argument necessary, but harmless
    	retval.Tree = (CommonTree)adaptor.ErrorNode(input, (IToken) retval.Start, input.LT(-1), re);

        }
        finally 
    	{
        }
        return retval;
    }
    // $ANTLR end "sexpr"

    public class item_return : ParserRuleReturnScope
    {
        private CommonTree tree;
        override public object Tree
        {
        	get { return tree; }
        	set { tree = (CommonTree) value; }
        }
    };

    // $ANTLR start "item"
    // Rule.g:22:1: item : ( atom | list );
    public RuleParser.item_return item() // throws RecognitionException [1]
    {   
        RuleParser.item_return retval = new RuleParser.item_return();
        retval.Start = input.LT(1);

        CommonTree root_0 = null;

        RuleParser.atom_return atom3 = default(RuleParser.atom_return);

        RuleParser.list_return list4 = default(RuleParser.list_return);



        try 
    	{
            // Rule.g:23:2: ( atom | list )
            int alt1 = 2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0 >= SYMBOL && LA1_0 <= NUMBER)) )
            {
                alt1 = 1;
            }
            else if ( (LA1_0 == LPAREN) )
            {
                alt1 = 2;
            }
            else 
            {
                NoViableAltException nvae_d1s0 =
                    new NoViableAltException("", 1, 0, input);

                throw nvae_d1s0;
            }
            switch (alt1) 
            {
                case 1 :
                    // Rule.g:23:4: atom
                    {
                    	root_0 = (CommonTree)adaptor.GetNilNode();

                    	PushFollow(FOLLOW_atom_in_item86);
                    	atom3 = atom();
                    	state.followingStackPointer--;

                    	adaptor.AddChild(root_0, atom3.Tree);

                    }
                    break;
                case 2 :
                    // Rule.g:24:4: list
                    {
                    	root_0 = (CommonTree)adaptor.GetNilNode();

                    	PushFollow(FOLLOW_list_in_item92);
                    	list4 = list();
                    	state.followingStackPointer--;

                    	adaptor.AddChild(root_0, list4.Tree);

                    }
                    break;

            }
            retval.Stop = input.LT(-1);

            	retval.Tree = (CommonTree)adaptor.RulePostProcessing(root_0);
            	adaptor.SetTokenBoundaries(retval.Tree, (IToken) retval.Start, (IToken) retval.Stop);
        }
        catch (RecognitionException re) 
    	{
            ReportError(re);
            Recover(input,re);
    	// Conversion of the second argument necessary, but harmless
    	retval.Tree = (CommonTree)adaptor.ErrorNode(input, (IToken) retval.Start, input.LT(-1), re);

        }
        finally 
    	{
        }
        return retval;
    }
    // $ANTLR end "item"

    public class list_return : ParserRuleReturnScope
    {
        private CommonTree tree;
        override public object Tree
        {
        	get { return tree; }
        	set { tree = (CommonTree) value; }
        }
    };

    // $ANTLR start "list"
    // Rule.g:27:1: list : LPAREN ( item )* RPAREN ;
    public RuleParser.list_return list() // throws RecognitionException [1]
    {   
        RuleParser.list_return retval = new RuleParser.list_return();
        retval.Start = input.LT(1);

        CommonTree root_0 = null;

        IToken LPAREN5 = null;
        IToken RPAREN7 = null;
        RuleParser.item_return item6 = default(RuleParser.item_return);


        CommonTree LPAREN5_tree=null;
        CommonTree RPAREN7_tree=null;

        try 
    	{
            // Rule.g:28:2: ( LPAREN ( item )* RPAREN )
            // Rule.g:28:4: LPAREN ( item )* RPAREN
            {
            	root_0 = (CommonTree)adaptor.GetNilNode();

            	LPAREN5=(IToken)Match(input,LPAREN,FOLLOW_LPAREN_in_list104); 
            		LPAREN5_tree = (CommonTree)adaptor.Create(LPAREN5);
            		adaptor.AddChild(root_0, LPAREN5_tree);

            	// Rule.g:28:11: ( item )*
            	do 
            	{
            	    int alt2 = 2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0 == LPAREN || (LA2_0 >= SYMBOL && LA2_0 <= NUMBER)) )
            	    {
            	        alt2 = 1;
            	    }


            	    switch (alt2) 
            		{
            			case 1 :
            			    // Rule.g:28:11: item
            			    {
            			    	PushFollow(FOLLOW_item_in_list106);
            			    	item6 = item();
            			    	state.followingStackPointer--;

            			    	adaptor.AddChild(root_0, item6.Tree);

            			    }
            			    break;

            			default:
            			    goto loop2;
            	    }
            	} while (true);

            	loop2:
            		;	// Stops C# compiler whining that label 'loop2' has no statements

            	RPAREN7=(IToken)Match(input,RPAREN,FOLLOW_RPAREN_in_list109); 
            		RPAREN7_tree = (CommonTree)adaptor.Create(RPAREN7);
            		adaptor.AddChild(root_0, RPAREN7_tree);


            }

            retval.Stop = input.LT(-1);

            	retval.Tree = (CommonTree)adaptor.RulePostProcessing(root_0);
            	adaptor.SetTokenBoundaries(retval.Tree, (IToken) retval.Start, (IToken) retval.Stop);
        }
        catch (RecognitionException re) 
    	{
            ReportError(re);
            Recover(input,re);
    	// Conversion of the second argument necessary, but harmless
    	retval.Tree = (CommonTree)adaptor.ErrorNode(input, (IToken) retval.Start, input.LT(-1), re);

        }
        finally 
    	{
        }
        return retval;
    }
    // $ANTLR end "list"

    public class atom_return : ParserRuleReturnScope
    {
        private CommonTree tree;
        override public object Tree
        {
        	get { return tree; }
        	set { tree = (CommonTree) value; }
        }
    };

    // $ANTLR start "atom"
    // Rule.g:31:1: atom : ( SYMBOL | NUMBER );
    public RuleParser.atom_return atom() // throws RecognitionException [1]
    {   
        RuleParser.atom_return retval = new RuleParser.atom_return();
        retval.Start = input.LT(1);

        CommonTree root_0 = null;

        IToken set8 = null;

        CommonTree set8_tree=null;

        try 
    	{
            // Rule.g:32:2: ( SYMBOL | NUMBER )
            // Rule.g:
            {
            	root_0 = (CommonTree)adaptor.GetNilNode();

            	set8 = (IToken)input.LT(1);
            	if ( (input.LA(1) >= SYMBOL && input.LA(1) <= NUMBER) ) 
            	{
            	    input.Consume();
            	    adaptor.AddChild(root_0, (CommonTree)adaptor.Create(set8));
            	    state.errorRecovery = false;
            	}
            	else 
            	{
            	    MismatchedSetException mse = new MismatchedSetException(null,input);
            	    throw mse;
            	}


            }

            retval.Stop = input.LT(-1);

            	retval.Tree = (CommonTree)adaptor.RulePostProcessing(root_0);
            	adaptor.SetTokenBoundaries(retval.Tree, (IToken) retval.Start, (IToken) retval.Stop);
        }
        catch (RecognitionException re) 
    	{
            ReportError(re);
            Recover(input,re);
    	// Conversion of the second argument necessary, but harmless
    	retval.Tree = (CommonTree)adaptor.ErrorNode(input, (IToken) retval.Start, input.LT(-1), re);

        }
        finally 
    	{
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


	private void InitializeCyclicDFAs()
	{
	}

 

    public static readonly BitSet FOLLOW_list_in_sexpr72 = new BitSet(new ulong[]{0x0000000000000000UL});
    public static readonly BitSet FOLLOW_EOF_in_sexpr74 = new BitSet(new ulong[]{0x0000000000000002UL});
    public static readonly BitSet FOLLOW_atom_in_item86 = new BitSet(new ulong[]{0x0000000000000002UL});
    public static readonly BitSet FOLLOW_list_in_item92 = new BitSet(new ulong[]{0x0000000000000002UL});
    public static readonly BitSet FOLLOW_LPAREN_in_list104 = new BitSet(new ulong[]{0x00000000000000F0UL});
    public static readonly BitSet FOLLOW_item_in_list106 = new BitSet(new ulong[]{0x00000000000000F0UL});
    public static readonly BitSet FOLLOW_RPAREN_in_list109 = new BitSet(new ulong[]{0x0000000000000002UL});
    public static readonly BitSet FOLLOW_set_in_atom0 = new BitSet(new ulong[]{0x0000000000000002UL});

}
}