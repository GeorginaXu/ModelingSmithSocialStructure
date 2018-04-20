%a function that takes in a graph G and a node x as input
%given the distance_matrix of x, output a probability matrix of x
%each entry of the probability matrix represents the prob for x to form an
%edge with other nodes
function[prob_matrix] = computeProbability(G,x,method,S,J,P,F)

if strcmp(method,"distance")
    %calculate the distance matrix of x
    distance_matrix = computeDistance(G,x);
    
    %initialize the prob_matrix of x
    prob_matrix = zeros(1,size(distance_matrix,2));
    
    %iterate over each entry in distance matrix
    for d = 1:size(distance_matrix,2)
        D = distance_matrix(1, d);%the distance between x and d
        %if the distance is 0 or there is already an edge between the two nodes
        if(D == 0 || G(x,d) == 1)
            prob_matrix(1,d) = 0;       %make its probability 0
        else
            %else, give it a certain probability based on D, (D>=2)
            if D == 2
                p = 0.4;
            elseif D == 3
                p = 0.2;
            else
                p = 0.1;
            end
            %update the probability matrix
            prob_matrix(1,d) = p;
        end
    end
    
elseif strcmp(method,"richer")
    prob_matrix = sum(G);   %prob_matrix is the degrees of all nodes
    if(all(sum(G) == 0) == 1)   %check if all degrees are 0
        for u = 1:length(G)     %if so, randomly give each node an edge
            v = randi(length(G));
            while v == u
                v = randi(length(G));
            end
            G(u,v) = 1;
            G(v,u) = 1;
        end
    end
    prob_matrix = sum(G);   %after each node has some degree, set pro_matrix equal to all degrees of nodes
    prob_matrix(1,x) = 0;   %set the probability for x itself to 0
    for u = 1:length(G)
        if(G(x,u) == 1)     %if there exists an edge between x and u already, set the probability to 0
            prob_matrix(1,u) = 0;
        end
    end 
    
elseif strcmp(method, "classyear")
    %initialize the prob_matrix of x
    prob_matrix = zeros(1,size(length(G),1));
    %get the class year of node x
    classOfX = computeClass(x,S,J,P,F);
    for u = 1:length(G)
        %if u is x or there exists an edge between u and x already
        if (u == x || G(u,x) == 1)  
            prob_matrix(1,u) = 0;   %set the probability to 0
        
        %if u and x are in the same class year, set high probability
        elseif(classOfX == computeClass(u,S,J,P,F))
            prob_matrix(1,u) = 0.4;
        
        %if u and x are 1 class year apart
        elseif(abs(classOfX - computeClass(u,S,J,P,F)) == 1)
            prob_matrix(1,u) = 0.3;
        
        %if u and x are 2 class years apart
        elseif(abs(classOfX - computeClass(u,S,J,P,F)) == 2)
            prob_matrix(1,u) = 0.2;
            
        %if u and x are 3 class years apart
        elseif(abs(classOfX - computeClass(u,S,J,P,F)) == 3)
            prob_matrix(1,u) = 0.1;
        end
    end
end 