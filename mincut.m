%a function that implements Karger-Stein algorithm to find the min edge cut
%of graph with adjacency matrix A with a probability of correctness p
function lamda = mincut(A,p)
    if ~conncomp(graph(A))
        error('Error:  input graph is not connected.')
    end
    if min(sum(A))==length(A)-1%this means A is a complete graph
        lamda=length(A)-1;
        return
    end
    n=length(A);
    t=log(1/(1-p))*n*(n-1)/2;
    lamda=inf;
    for i=1:t
        temp=Karger(A);
        if temp<lamda
            lamda=temp;
        end
    end
    
% 
% %initialize the set of supernodes to be the set of the n nodes
% supernodes=cell(1,n);
% i=1;
% for u=1:n
%     superedges{i}=u;
%     i=i+1;
% end
% %initialize the set of superedges to contain all edges in the graph
% superedges=cell(1,m);
% i=1;
% for u=1:n
%     for v=1:n
%         if u<v && A(u,v)==1
%             superedges{i}=[u,v];
%             i=i+1;
%         end
%     end
% end
% while length(supernodes)>2
%     %uniform random edge from superedges
%     index=randi(length(superedges));
%     edge=superedges{index};
% 
%     merge(edge(1),edge(2),supernodes,A);
%     %remove edge from superedges
%     superedges{index}=[];
%     superedges=superedges(~cellfun('isempty',superedges));  
% end
