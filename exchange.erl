%% @author syeda
%% @doc @todo Add description to exchange.


-module(exchange).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start/0,masterReceiver/0]).
-import(calling,[pReceiver/1]).



%% ====================================================================
%% Internal functions
%% ====================================================================

start()->
	register(master, spawn(exchange,masterReceiver, [])),
	{ok,Ls}=file:consult("c:/Erlang Code Rep/project/src/calls.txt"),
	%io:fwrite("~p",[Ls]),
	Db=maps:from_list(Ls),
	%io:fwrite("~p",[db]),
	%io:fwrite("~s",[ok])
	lists:foreach(fun(A)->
						create(A),
						io:fwrite("~w: ~w ~n",[A,maps:get(A, Db)])
					end,maps:keys(Db)),
							  
	lists:foreach(fun(K)->
						lists:foreach(fun(J)->
											whereis(J) ! {"rec", K}
						  					%io:fwrite("~w: ~w ~n",[K,maps:get(K, Db)])
				  							end,maps:get(K,Db))
					  					%io:fwrite("J")
				  						end,maps:keys(Db)).
create(Pid)->
			register(Pid,spawn(calling, pReceiver,[Pid])).		
	
masterReceiver()->
				receive
					{rep,Sender,Receiver,Timestamp}->
						io:fwrite("~w received reply message from ~w [~w]~n",[Receiver,Sender,Timestamp]),
						masterReceiver();
					{ic,Sender,Receiver,Timestamp}->
						io:fwrite("~w received intro message from ~w [~w]~n",[Receiver,Sender,Timestamp]),
						masterReceiver()
				after 1500->
						io:fwrite("Master has received no replies for 1.5 seconds, ending...~n")
				end.			
						
						