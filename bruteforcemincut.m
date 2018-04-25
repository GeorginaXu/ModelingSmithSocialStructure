%compute min edge cut 
% function result=bruteforcemincut(A)
% if ~issymmetric(A)
%     error('Error:  input adjacency matrix is not symmetrical.')
% end
% result=min(sum(A));%maximum number of min cut
% A1=A;
% 
% %check whether result is 1
% for u=1:length(A)
%     for v=1:length(A)
%         if u<v && A(u,v)==1
%             A1(u,v)=0;
%             A1(v,u)=0;
%             d=computeDistance(A1,u);
%             if ismember(inf,d)%A1 is disconnected
%                 result=1;
%                 u
%                 v
%                 return 
%             end
%             A1=A;%reset A1 back to A
%         end
%     end
% end
% %check whether result is 2
%%
function lambda=bruteforcemincut(A)
%min edge cut is at least the min node cut, at most the min degree
maxbound=min(sum(A));
lowerbound=minnodecut(A);
if maxbound==lowerbound
    lambda=maxbound;
    return
end
error('loop through all possible selections of lowerbound edges and see if any selection disconnect the graph')




%%
% G=graph(A);
% 
% for i=1:height(G.Edges)
%     if conncomp(rmedge(G,i))>1
%         lambda=1;
%         return
%     end
% end
% 
% for i=1:height(G.Edges)
%     for j=i+1:height(G.Edges)
%         if conncomp(rmnode(G,[i,j]))>1
%             lambda=2;
%             return
%         end
%     end
% end
% 
% for i=1:height(G.Edges)
%     for j=i+1:height(G.Edges)
%         for k=j+1:height(G.Edges)
%             if conncomp(rmnode(G,[i,j,k]))>1
%                 lambda=3;
%                 return
%             end
%         end
%     end
% end
