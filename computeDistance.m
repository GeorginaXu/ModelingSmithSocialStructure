function [distance_matrix] = computeDistance(G, x)

%initialize distance matrix such that each entry is infinity
distance_matrix = ones(1,size(G, 1))* 20000;

%initialize the starred matrix. 0 represents unstarred.
starred_matrix = zeros(1,size(G, 1));

%label the start_node 0
distance_matrix(1,x) = 0;

%a helper matrix that keeps track of nodes that haven't been starred
helper = distance_matrix;

%check if there is any unstarred node
for t = 1:size(G, 1)
    %find the minimum label among all the unstarred nodes
    [M, I] = min(helper);
    
    %star the minimum label, 1 represents starred
    starred_matrix(1,I) = 1;
    
    %change the label of the starred node to infinity in helper matrix,
    %representing that it has been starred
    helper(1,I) = 50000;
    
    %iterate through all the nodes
    for i = 1:size(G, 1)        
        %check if there is an edge between starred node and node i
        %also check if i is unstarred
        if (G(I,i) ~= 0 && starred_matrix (1, i) ~= 1)
            %calculate the distance from starred node to node i
            new_distance = M + G(I,i);
            
            %check if the new distance is smaller than the original label
            if(new_distance < distance_matrix(1,i))
                %update the distance from starred node to node i
                distance_matrix(1,i) = new_distance;
                helper(1,i) = new_distance;
            end
        end
    end
end
            
            
            
            
            
            